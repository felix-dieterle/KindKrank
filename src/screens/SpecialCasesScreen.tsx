import React from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  SafeAreaView,
  ScrollView,
} from 'react-native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { RootStackParamList } from '../types';

type Props = {
  navigation: NativeStackNavigationProp<RootStackParamList, 'SpecialCases'>;
};

interface SpecialCase {
  emoji: string;
  title: string;
  description: string;
}

const SPECIAL_CASES: SpecialCase[] = [
  {
    emoji: '🏥',
    title: 'Stationärer Krankenhausaufenthalt',
    description:
      'Auch beim Krankenhausaufenthalt des Kindes haben Eltern Anspruch auf Kinderkrankentage. Die Bescheinigung stellt das Krankenhaus aus. Der Anspruch besteht unabhängig vom Alter des Kindes, wenn ein Elternteil medizinisch notwendig mitaufgenommen wird.',
  },
  {
    emoji: '♿',
    title: 'Kind mit Behinderung',
    description:
      'Bei Kindern mit Behinderung, die auf Betreuung angewiesen sind, gibt es keine Altersgrenze (keine 12-Jahres-Grenze). Der Anspruch auf Kinderkranktage gilt unbegrenzt.',
  },
  {
    emoji: '👶',
    title: 'Mehrere Kinder',
    description:
      'Für jedes Kind unter 12 Jahren haben Sie 10 (bzw. 20 bei Alleinerziehenden) Tage Anspruch, jedoch maximal 25 (bzw. 50) Tage insgesamt. Beispiel: 2 Kinder = 20 Tage je Elternteil, aber max. 25.',
  },
  {
    emoji: '📅',
    title: 'Übertragung von Tagen',
    description:
      'Seit 2024 können Kinderkranktage zwischen Elternteilen übertragen werden, wenn ein Elternteil seinen Anspruch nicht vollständig nutzt. Bitte fragen Sie bei Ihrer Krankenkasse nach, da die Regelungen sich ändern können.',
  },
  {
    emoji: '🏢',
    title: 'Arbeitgeber verweigert Freistellung',
    description:
      'Der Arbeitgeber ist verpflichtet, die Freistellung zu gewähren (§ 45 SGB V). Verweigert er dies, haben Arbeitnehmer trotzdem Anspruch auf die Freizeit – konsultieren Sie bei Problemen einen Fachanwalt für Arbeitsrecht oder den Betriebsrat.',
  },
  {
    emoji: '💰',
    title: 'Beamte & Selbstständige',
    description:
      'Beamte sind in der Regel über den Dienstherren abgesichert und erhalten Sonderurlaub. Selbstständige ohne gesetzliche Krankenversicherung haben keinen Anspruch auf Kinderkrankengeld, außer sie sind freiwillig gesetzlich versichert und haben Krankengeld-Anspruch vereinbart.',
  },
  {
    emoji: '🔄',
    title: 'Kinderkranktag läuft aus',
    description:
      'Sollte das Kind länger krank sein als die verbleibenden Kinderkranktage, muss entweder Urlaub genommen, unbezahlter Urlaub beantragt oder ein anderer Elternteil die Betreuung übernehmen. In außergewöhnlichen Situationen können Bundesländer Sonderregelungen einführen (z. B. wie in der COVID-Pandemie).',
  },
  {
    emoji: '📋',
    title: 'Kinderkrankenschein nachträglich',
    description:
      'Der Kinderkrankenschein sollte möglichst beim ersten Arztbesuch ausgestellt werden. Eine nachträgliche Ausstellung ist zwar möglich, aber komplizierter und manche Krankenkassen akzeptieren ihn dann nicht für alle Tage.',
  },
];

export default function SpecialCasesScreen({ navigation }: Props) {
  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.content}>
        <Text style={styles.title}>Sonderfälle &amp; Ausnahmen</Text>
        <Text style={styles.description}>
          Hier finden Sie wichtige Sonderfälle und Ausnahmen rund um das Thema Kinderkranktage.
        </Text>

        {SPECIAL_CASES.map((sc, index) => (
          <View key={index} style={styles.caseCard}>
            <Text style={styles.caseEmoji}>{sc.emoji}</Text>
            <View style={styles.caseContent}>
              <Text style={styles.caseTitle}>{sc.title}</Text>
              <Text style={styles.caseText}>{sc.description}</Text>
            </View>
          </View>
        ))}

        <View style={styles.disclaimer}>
          <Text style={styles.disclaimerText}>
            ⚠️ Diese Informationen sind allgemeiner Natur und können sich ändern. Bitte wenden Sie
            sich bei konkreten Fragen an Ihre Krankenkasse oder einen Rechtsanwalt.
          </Text>
        </View>

        <TouchableOpacity
          style={styles.backButton}
          onPress={() => navigation.navigate('Home')}
          accessibilityLabel="Zurück zur Startseite"
        >
          <Text style={styles.backButtonText}>↩ Zurück zur Startseite</Text>
        </TouchableOpacity>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#E6F4FE',
  },
  content: {
    padding: 20,
    paddingBottom: 40,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#1A5276',
    textAlign: 'center',
    marginBottom: 10,
    marginTop: 8,
  },
  description: {
    fontSize: 15,
    color: '#555',
    textAlign: 'center',
    lineHeight: 22,
    marginBottom: 24,
  },
  caseCard: {
    backgroundColor: '#fff',
    borderRadius: 14,
    padding: 16,
    flexDirection: 'row',
    alignItems: 'flex-start',
    marginBottom: 12,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.08,
    shadowRadius: 3,
  },
  caseEmoji: {
    fontSize: 28,
    marginRight: 14,
    marginTop: 2,
  },
  caseContent: {
    flex: 1,
  },
  caseTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#1A5276',
    marginBottom: 6,
  },
  caseText: {
    fontSize: 14,
    color: '#444',
    lineHeight: 21,
  },
  disclaimer: {
    backgroundColor: '#FFF8E1',
    borderRadius: 10,
    padding: 14,
    marginTop: 8,
    marginBottom: 20,
    borderLeftWidth: 4,
    borderLeftColor: '#F39C12',
  },
  disclaimerText: {
    fontSize: 13,
    color: '#555',
    lineHeight: 20,
  },
  backButton: {
    backgroundColor: '#2E86AB',
    borderRadius: 12,
    paddingVertical: 14,
    alignItems: 'center',
  },
  backButtonText: {
    color: '#fff',
    fontSize: 15,
    fontWeight: 'bold',
  },
});
